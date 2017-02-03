# Lello

Author: Benjamin Lopez

Email: s61250@beuth-hochschule.de

Date: 02.01.2017 - 04.04.2017

## Synopsis

Bachelor thesis for "Medieninformatik" at the "Beuth Hochschule für Technik Berlin".

Original Topic: Entwicklung und Evaluation eines e-Kompetenz-Verzeichnisses mit REST-API und eines automatisierten Crawlers zur Datensammlung.

Repository for e-Competences/Skills of Badges. This software crawls and parses e-Competences/Skills and saves them in a Neo4J-Graphic database. Over an API, these e-Competences can be queried using keywords, returning the most likely suggestions for that term. The software represents the first version for the backend of a e-Competence search engine.

## Code Example

Show what the library does as concisely as possible, developers should be able to figure out **how** your project solves their problem by looking at the code example. Make sure the API you are showing off is obvious, and that your code is short and concise.

## Motivation

A short description of the motivation behind the creation and maintenance of the project. This should explain **why** the project exists.

## Installation

Requirement: Java 8, Maven 3.3, MySQL Database

Download and install neo4J database from https://neo4j.com/download/

Compile and run the code

mvn clean install

mvn spring-boot:run

## API Reference

Depending on the size of the project, if it is small and simple enough the reference docs can be added to the README. For medium size to larger projects it is important to at least provide a link to where the API reference docs live.

## Tests

Describe and show how to run the tests with code examples.
