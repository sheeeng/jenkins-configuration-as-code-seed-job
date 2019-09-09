#!/usr/bin/env groovy

import java.util.regex.Matcher
import java.util.regex.Pattern

// String[] stringArray = ['git@github.com:sheeeng/jaunty-java.git']

// assert stringArray instanceof String[]
// assert !(stringArray instanceof List)

String regex = /^somedata(:somedata)*$/

// assert matches...
assert "somedata" ==~ regex
assert "somedata:somedata" ==~ regex
assert "somedata:somedata:somedata" ==~ regex

String gitRegularExpression = /(?:git|ssh|https?|git@[-\w.]+):(\/\/)?(.*?)(\.git)(\/?|\#[-\d\w._]+?)$/
assert "git@github.com:sheeeng/jaunty-java.git" ==~ gitRegularExpression

String gitHubRegex = /(?m)git@github.com:(?<projectOrganization>[\w]+)\/(?<projectName>(?!\.git$)[\w_\d-]+)\.git$/
def matchMaker = 'git@github.com:sheeeng/jaunty-java.git' =~ gitHubRegex
if ( matchMaker.matches() ) {
    println "Matched...."

    assert matchMaker[0][1] == 'sheeeng'
    assert matchMaker[0][2] == 'jaunty-java'
    assert matchMaker.group( 'projectOrganization' ) == 'sheeeng'
    assert matchMaker.group( 'projectName' ) == 'jaunty-java'

    matchMaker.reset()

    int matcherIndex = 0;
    while(matchMaker.find()) {
        matcherIndex++;
        System.out.println("Found: "
            + matcherIndex
            + " : "
            + matchMaker.start()
            + " - "
            + matchMaker.end());
    }

    matchMaker.reset()

    while(matchMaker.find()) {
        System.out.println("Found: "
            + matchMaker.group(1)
            + ' '
            + matchMaker.group(2));
    }
}


// https://gist.github.com/EwanDawson/2407215
// Using Matcher object returned by =~ operator
matcher1 = "Hello world v1.01" =~ /.* v(\S*)/
if (matcher1.matches()) version = matcher1[0][1]
assert version == "1.01"
// We can make this a little tidier using the 'with' method
version = ("Hello world v1.01" =~ /.* v(\S*)/).with { matches() ? it[0][1] : null }
assert version == "1.01"

// https://stackoverflow.com/a/15664805
def matcher = 'John 19' =~ /(?<name>\w+) (?<age>\d+)/
if ( matcher.matches() ) {
    println "Matched."
    assert matcher.group( 'name' ) == 'John'
    assert matcher.group( 'age' ) == '19'
} else {
    println "No Match Found."
}
