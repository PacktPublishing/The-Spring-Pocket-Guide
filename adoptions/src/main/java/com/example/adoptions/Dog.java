package com.example.adoptions;

import org.springframework.data.annotation.Id;

// <1>
record Dog(@Id int id, String name, String owner, String description) {
}
