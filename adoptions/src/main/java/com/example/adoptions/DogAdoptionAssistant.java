package com.example.adoptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

// mcp
@Controller
@ResponseBody
class DogAdoptionAssistant {

	private final ChatClient singularity;

	private final QuestionAnswerAdvisor questionAnswerAdvisor;

	private final PromptChatMemoryAdvisor promptChatMemoryAdvisor;

	DogAdoptionAssistant(ChatClient singularity, QuestionAnswerAdvisor questionAnswerAdvisor,
			PromptChatMemoryAdvisor promptChatMemoryAdvisor) {
		this.singularity = singularity;
		this.questionAnswerAdvisor = questionAnswerAdvisor;
		this.promptChatMemoryAdvisor = promptChatMemoryAdvisor;
	}

	@GetMapping("/{user}/assistant")
	String assistant(@PathVariable String user, @RequestParam String question) {
		return this.singularity.prompt(question)
			.advisors(this.promptChatMemoryAdvisor, this.questionAnswerAdvisor)
			.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
			.call()
			.content();
	}

}

@Configuration
class McpConfiguration {

	@Bean
	McpSyncClient mcpSyncClient() {
		var mcp = McpClient.sync(HttpClientSseClientTransport.builder("http://localhost:8081").build()).build();
		mcp.initialize();
		return mcp;
	}

}

@Configuration
@RegisterReflectionForBinding(OpenAiChatOptions.class)
class DogAdoptionAssistantConfiguration {

	@Bean
	PromptChatMemoryAdvisor promptChatMemoryAdvisor(DataSource dataSource) {
		var jdbcChatMemoryRepository = JdbcChatMemoryRepository.builder().dataSource(dataSource).build();
		var messageWindowChatMemory = MessageWindowChatMemory.builder()
			.chatMemoryRepository(jdbcChatMemoryRepository)
			.build();
		return PromptChatMemoryAdvisor.builder(messageWindowChatMemory).build();
	}

	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {
		return new QuestionAnswerAdvisor(vectorStore);
	}

	@Bean
	ChatClient chatClient(McpSyncClient mcpClient, VectorStore vectorStore,
			DogVectorStoreInitializer dogVectorStoreInitializer,
			DogAdoptionSchedulingService dogAdoptionSchedulingService, ChatClient.Builder builder) {
		dogVectorStoreInitializer.initialize();
		var system = """
				You are an AI powered assistant to help people adopt a dog from the adoption
				agency named Pooch Palace with locations in São Paulo, Tokyo, Singapore, Paris,
				New Delhi, Barcelona, San Francisco, and London. Information about the dogs available
				will be presented below. If there is no information, then return a polite response suggesting we
				don't have any dogs available.
				""";
		return builder//
			.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))//
			.defaultSystem(system)//
			.defaultTools(dogAdoptionSchedulingService)//
			// .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpClient))
			.build();
	}

}

@Component
class DogVectorStoreInitializer {

	private final DogRepository repository;

	private final VectorStore vectorStore;

	private final JdbcClient db;

	DogVectorStoreInitializer(VectorStore vectorStore, DogRepository repository, JdbcClient db) {
		this.repository = repository;
		this.vectorStore = vectorStore;
		this.db = db;
	}

	@Async
	void initialize() {

		var countOfRecordsInVectorStore = (int) this.db.sql("select count(*) from vector_store")
			.query(Integer.class)
			.single();
		if (countOfRecordsInVectorStore != 0)
			return;

		repository.findAll().forEach(dog -> {
			var content = "id: %s, name: %s, description: %s".formatted(dog.id(), dog.name(), dog.description());
			var dogument = new Document(content);
			System.out.println("adding [" + content + "]");
			vectorStore.add(List.of(dogument));
		});
	}

}

@Component
class DogAdoptionSchedulingService {

	private final ObjectMapper om;

	DogAdoptionSchedulingService(ObjectMapper om) {
		this.om = om;
	}

	@Tool(description = "schedule an appointment to adopt a dog")
	String scheduleDogAdoptionAppointment(@ToolParam(description = "the id of the dog") int dogId,
			@ToolParam(description = "the name of the dog") String name) throws Exception {
		System.out.println("scheduleDogAdoptionAppointment [dogId=" + dogId + ", name=" + name + "]");
		return this.om.writeValueAsString(Instant.now().plus(3, ChronoUnit.DAYS));
	}

}
