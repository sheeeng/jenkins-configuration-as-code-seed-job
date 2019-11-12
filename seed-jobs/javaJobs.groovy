#!/usr/bin/env groovy

import utilities.JobFactory
def factory = new JobFactory(this)

factory.createFolder(
    name: "java",
    displayName: "Java",
    description: "Description of Java Projects."
)

factory.createFolder(name: "java/acme")
factory.createFolder(name: "java/acme/anotherFolder")
factory.createMultiBranchPipeline(
    name: "java/acme/jaunty-java",
    jobDescription: "Multibranch Pipeline Job for Java project.",
    id: "3cd0df22-2692-465c-8ce8-c9a12ad6156a",
    gitUrl: "git@github.com:sheeeng/jaunty-java.git",
    includes: "*",
    discardOldNumToKeep: 3,
    discardOldDaysToKeep: 5,
    triggerScm: "H/5 * * * *",
    jenkinsfilePath: "Jenkinsfile"
)
