**Secret Santa Match Generator**

Requirements:

_Algorithm Discussion:_

Starting with the collection of FamilyMembers, pick the first and create a match as both a Giver and a Receiver.
Remove the starter from the pool to prevent touching it again as we move through the collection. 
Pick a random FamilyMember from the remaining pool and test to see if it has previously been, matched, 
if so, remove it from the pool and pick another from the remaining list. Doing this prevents a FamilyMember that 
has already been matched from being tested multiple times. As the pool shrinks, the number of tests gets smaller, 
improving the efficiency of the test.

_Extensibilty Discussion:_


_Datastore Discussion:_


_Scalability Discussion:_

The solution I build wouldn't scale well as structured. I think to scale an application to the scope of the size of 
the USA population, an entirely different architectural pattern would need to be employed, and the end result would be
far more complex than what I constructed for the problem presented. If I were asked to build an app with that scale in 
mind, I would propose something to the effect of:

1. Segment the data into small chunks and pass those chunks to an array of processing applications 
that in turn pass their results to a second set of applications that combine the results and eliminate duplication 
within the entire result set. 
2. Map-Reduce pattern
3. Shared data via a transactional data store
