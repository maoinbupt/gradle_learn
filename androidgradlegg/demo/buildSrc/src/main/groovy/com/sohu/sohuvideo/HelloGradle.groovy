package com.sohu.sohuvideo

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloGradle implements Plugin<Project> {

    void apply(Project project) {
        //1.gradle extension
        project.extensions.create("greeting", GreetingExtension)
        project.task('hello').doLast {
            println "${project.greeting.message} from the ${project.greeting.greeter}"
        }

        // 2.gradle container
        def greetings = project.container(GreetingContainer)
        project.extensions.greetings = greetings
        project.task('hellos').doLast {
            greetings.all { // 遍历 greetings 
                println "${it.msgList*.toString()} from ${it.name}"
            }
        }
    }
}


class GreetingExtension {
    String message = "Hello!"
    String greeter = "Gradle plugin "
}

class GreetingContainer {
    final String name
    List<String> msgList = []

    GreetingContainer(String name) { this.name = name }

    void message(String... args) { msgList += Arrays.asList(args) }
}


