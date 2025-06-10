package com.mihir.notesapi; // this is for organizing files in folder structure e.g. "com/mihir/notesapi"

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Spring sees this and says: “Hey, this class will have methods to handle HTTP requests, and the return values should be sent as the response body.”

public class HelloController {

    @GetMapping("/") // Maps HTTP GET requests for the root URL / to this method.
    // If you visit http://localhost:8080/ in your browser, this method runs.

    // declaring a public method (function) -> public *datatype* *name of the method* (*parameters/args*) { }
    public String home() {
        return "Hello";
    }

}
