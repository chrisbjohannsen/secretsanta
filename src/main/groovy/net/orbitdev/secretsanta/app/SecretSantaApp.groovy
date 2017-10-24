package net.orbitdev.secretsanta.app

import net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.db.InMemoryFamilyMemberStore
import net.orbitdev.secretsanta.db.InMemorySecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.engine.DefaultMatchEngine
import net.orbitdev.secretsanta.engine.IMatchEngine
import net.orbitdev.secretsanta.service.SecretSantaService

/**
 * Application will generate a set of Secret Santa matches based on a list of family
 * members.
 *
 * Rules:
 *  A member cannot be its own match.
 *  Each member should match one and only one as a Giver
 *  Each member should match one and only one as a Receiver
 *
 */
class SecretSantaApp {

    /**
     * Initial implementation: generate FamilyMembers programmatically and print to console
     * First enhancement:   Take a file path to a csv/json file to be parsed into family member store
     * Second enhancement:  Add flag to print output to a file
     * @param args
     */
    static void main(args) {

        def familyMemberStore = createFamilyRelationships(fillInMemoryStore(1000, new InMemoryFamilyMemberStore()))

        def secretSantaStore = new InMemorySecretSantaStore()

        IMatchEngine engine = new DefaultMatchEngine(secretSantaStore)

        def secretSantaService = new SecretSantaService(familyMemberStore, secretSantaStore, engine)

        def start = System.currentTimeMillis()
        println "*** start generating matches ***"
        secretSantaService.generateMatches()
        def end = System.currentTimeMillis()
        println "*** ${secretSantaStore.matches.size()} matches generated in ${end - start}ms***"

        secretSantaStore.matches.each { key, value ->

            assert value.size() == 2
            assert secretSantaStore.matches.keySet().count { it == key } == 1

            value.each {
                assert !it.giver.immediateFamily.keySet().contains(it.receiver.id)
               //println it.giver.toString()
            }
        }


    }

    static IFamilyMemberStore fillInMemoryStore(int numberOfMembers, IFamilyMemberStore store) {
        (1..numberOfMembers).each {
            store.addMember(new FamilyMember(id: it, name: "Member_${it}"))
        }

        assert store.members.size() == numberOfMembers
        store
    }

    static IFamilyMemberStore createFamilyRelationships(IFamilyMemberStore store){
        int iterations = store.members.size()/20
        int iterationCount = 1

        def membersList = store.members.toList()

        for(iterationCount; iterationCount <= iterations; iterationCount ++){

            def batch = membersList.subList(iterationCount,iterationCount+5)
            batch[0].immediateFamily.put(batch[1],'spouse')
            batch[1].immediateFamily.put(batch[0],'spouse')
            batch[0].immediateFamily.put(batch[2],'parent')
            batch[2].immediateFamily.put(batch[0],'child')
            batch[0].immediateFamily.put(batch[3],'child')
            batch[1].immediateFamily.put(batch[3],'child')
            batch[3].immediateFamily.put(batch[0],'parent')
            batch[3].immediateFamily.put(batch[1],'parent')
            batch[1].immediateFamily.put(batch[4],'parent')
            batch[4].immediateFamily.put(batch[1],'child')
        }

        println "family store created"
        store
    }
}
