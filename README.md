**Secret Santa Match Generator**

_Requirements:_

Part one:

Imagine that every year your extended family does a "Secret Santa" gift exchange. For this gift
exchange, each person draws another person at random and then gets a gift for them. Write a
program that will choose a Secret Santa for everyone given a list of all the members of your
extended family. Obviously, a person cannot be their own Secret Santa.

Part two:

After the third year of having the Secret Santa gift exchange, you’ve heard complaints of having
the same Secret Santa year after year. Modify your program so that a family member can only
have the same Secret Santa once every 3 years.

Part three:

As your extended family has grown, members have gotten married and/or had children.
Families usually get gifts for members of their immediate family, so it doesn’t make a lot of
sense for anyone to be a Secret Santa for a member of their immediate family (spouse, parents,
or children). Modify your program to take this constraint into consideration when choosing
Secret Santas.

_Algorithm Discussion:_

Methodology:
To generate the metrics used to analyze the complexity I did some rudimentary tooling in the main method of the SecretSantaApp.groovy file. 
To measure execution time I captured start and end times of the SecretSantaService.generateMatches() method and printed the difference to the console 
then used a spreadsheet to track runs and calculate averages. 

To measure average heap sizes I used the jvisualvm tool packaged with Java8 JDK to monitor multiple runs, increasing the member count manually for each run set.  I took the spikes in the heap size and averaged for the run, with the assumption that the spikes
represented the max memory used per run.

I employed a simple groovy range closure to simplify the operation and ran each set 5 times. Sets started at 1000 inputs and increased by 1000 up to 4000, which was near the tipping point 
for the machine I was testing on.

Runs 1 & 3   | Runs 2 & 4
------------ | -------------
![1000 Member Run](1000Members.png) | ![2000 Member Run](2000Members.png)
![3000 Member Run](3000Members.png) | ![4000 Member Run](4000Members.png)

Charts were generated using the averages for the each run set and a trend line plot applied to each.

Test were completed on a Linux Mint VM hosted on a Windows 10 Lenovo Laptop. The machine has 32GB of RAM of which 24GB is dedicated to the VM. 


Runtime Characteristics: This is a linear-time algorithm, as the number of members increases, time to process increases proportionally. 
![Time vs Members Chart](chart.png)

Memory Characteristics: 
This is a linear-space algorithm, as the number of members increases, the memory required to complete the operations increase proportionally.
![Average Heap Size(MB) vs Total Number of Members](chart-2.png)

Description:

Starting with the collection of FamilyMembers, pick the first and create a match as both a Giver and a Receiver.
Remove the starter from the pool to prevent touching it again as we move through the collection. 
Pick a random FamilyMember from the remaining pool and test to see if it has previously been, matched, 
if so, remove it from the pool and pick another from the remaining list recursively. Doing this prevents a FamilyMember that 
has already been matched from being tested multiple times. As the pool shrinks, the number of tests gets smaller, 
improving the efficiency of the test.

_Extensibilty Discussion:_

In order to support simpler additions/subtractions of business rules defining whether a pair of family members can be matched I chose to implement a [Specification](https://en.wikipedia.org/wiki/Specification_pattern) pattern. I have used the pattern in prior projects as a validation mechanism and I felt it a good fit for the nature of the business rules in the app.

In terms of cost of adding new business rules, I find that the composability of the pattern higly benefitial as it allows one to create and test individual rules in isolation then compose a composite rule chain as a single object that can be injected into its consuming object. In practical terms for this app, one simple needs to add a new implementation of the CompositeSpecification<FamilyMember> and unit test. When satisfied that the implementation works as expected, it can be added to the MatchFamilyMemberSpecification composite spec that gets injected into the DefaultMatchEngine. 

_Datastore Discussion:_

In order to support variability in implmentation, i.e. in-memory vs persistent data store, I defined a set of interfaces, ISecretSantaStore and IFamilyMemberStore, to describe an api that consumers may relyabily program against without knowledge of the underlying implemenation. 

In practical terms, one would need to implement this interface as a facade over the chosen persistent data store apis and the application code would be non-the-wiser.

Based on the given requirements, I would choose a relational database management system that supports transactions to back this application. The transaction support will be important as the number of concurrent writes grows i.e. as the application is scaled up. 

MySql is a favorite choice due to its relativley small footprint, easy to understand SQL variant and freely availble tooling. It would likely work fine for a single user verison of the application. However, while reseaching, I found that it has some pretty tricky [gotchas](https://hashrocket.com/blog/posts/mysql-has-transactions-sorta) when it comes to transaction support, making it less desirable for the longer term.

While PostgreSQL is a bit less friendly, I think it a better choice for a couple of reasons. The transaction model is built with multiuser, concurrent applications in mind with the transaction isolation level configurable per session. Additionally, PostgreSQL has a strong open source support community which could come in helpful when things go sideways.

This application seems to lend itself to a combination NoSQL Key/Value, Graph database solution, however my lack of experience with either technology and the addition complexity for the stated goals disuade me from going down this path for the time being.

_Scalability Discussion:_

The first thing I would try to do to scale this application would be to add some worker threads to help parallelize match generation. However I think to scale an application to the scope of the size of the population of the US, an entirely different architectural pattern would need to be employed, and the end result would be far more complex than what I constructed for the problem presented. If I were asked to build an app with that scale in mind, I would propose something to the effect of:

1. Devise a mechanism to segment the data into small chunks and pass those chunks to an array of processing applications that in turn pass their results to a second set of applications that combine the results and eliminate duplication within the resulting matches. 
2. Implement a NoSQL Key/Value store that is highly available and partition tolerant to store the matches,sacrificing consistency across the nodes since it won't break anyones heart to get two gifts.
3. Investing the implementation of a Graph database to store the familyMembers and relationships. This may have the benefit of eliminating the need to code the rules for immediate family exclusions by leveraging the graph database to select groups to be matched that exclude members in said relationships. This is speculation on my part based on what very little I know about graph database technologies.
