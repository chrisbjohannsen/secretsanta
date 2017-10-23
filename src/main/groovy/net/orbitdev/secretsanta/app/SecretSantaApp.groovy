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

        def familyMemberStore = fillInMemoryStore(1000, new InMemoryFamilyMemberStore())
        def secretSantaStore = new InMemorySecretSantaStore()

        IMatchEngine engine = new DefaultMatchEngine(secretSantaStore)

        def secretSantaService = new SecretSantaService(familyMemberStore, secretSantaStore, engine)

        secretSantaService.generateMatches()

        println "${secretSantaStore.matches.size()} matches"
        secretSantaStore.matches.each { key, value ->

            assert value.size() == 2
            assert secretSantaStore.matches.keySet().count { it == key } == 1

            println "FamilyMember : ${key} - Record Count ${value.size()}"
            value.each {
                println "${it.toString()}"
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
}
