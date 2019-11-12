#!/usr/bin/env groovy

import utilities.JobFactory
def factory = new JobFactory(this)

factory.createFolder(
    name: "nodejs",
    displayName: "NodeJS",
    description: "Description of NodeJS Projects."
)

factory.createFolder(name: "nodejs/acme")
factory.createMultiBranchPipeline(
    name: "nodejs/acme/nervy-nodejs",
    jobDescription: "Multibranch Pipeline Job for NodeJS project of Acme team.",
    id: "9fd5b299-6c39-417c-97b9-815f40944555",
    gitUrl: "git@github.com:sheeeng/nervy-nodejs.git",
    includes: "*",
    discardOldNumToKeep: 3,
    discardOldDaysToKeep: 5,
    jenkinsfilePath: "Jenkinsfile"
)
