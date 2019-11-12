#!/usr/bin/env groovy

import utilities.JobFactory
def factory = new JobFactory(this)

factory.createFolder(
    name: "python",
    displayName: "Python",
    description: "Description of Python Projects."
)

factory.createFolder(name: "python/acme")
factory.createFolder(name: "python/acme/anotherFolder")
factory.createPipelineJob(
    name: "python/acme/precise-python",
    jobDescription: "Pipeline Job for Python project.",
    gitUrl: "git@github.com:sheeeng/precise-python.git",
    gitBranch: "master",
    jenkinsfilePath: "Jenkinsfile"
)
