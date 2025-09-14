package com.example.adoptions;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

// <1>
@Service
@Transactional
class MyTransactionalService {

	private final TransactionTemplate template;

	MyTransactionalService(TransactionTemplate template) {
		this.template = template;
	}

	void doSomethingWithTransactionTemplate() {
		// <2>
		this.template.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				// do something with the underlying transactional resource
				return null;
			}
		});
	}

	// <3>
	@Transactional(readOnly = true)
	void doSomethingWithTransactionalAnnotation() {
		// do something else with underlying transactional resource
	}

}
