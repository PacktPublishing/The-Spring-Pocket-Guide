package com.example.adoptions;

import com.example.service.grpc.Dog;
import com.example.service.grpc.DogsResponse;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// <1>
@Service
@Transactional
class DogsGrpcService extends com.example.service.grpc.AdoptionsGrpc.AdoptionsImplBase {

	private final DogService dogService;

	DogsGrpcService(DogService dogService) {
		this.dogService = dogService;
	}

	@Override
	public void all(Empty request, StreamObserver<DogsResponse> responseObserver) {

		// <2>
		var dogs = this.dogService//
			.all() //
			.stream() //
			.map(dog -> Dog//
				.newBuilder()//
				.setName(dog.name())//
				.setDescription(dog.description())//
				.setId(dog.id())//
				.build()//
			)//
			.toList();

		// <3>
		var dogsResponse = DogsResponse.newBuilder().addAllDogs(dogs).build();

		// <4>
		responseObserver.onNext(dogsResponse);
		responseObserver.onCompleted();
	}

}
