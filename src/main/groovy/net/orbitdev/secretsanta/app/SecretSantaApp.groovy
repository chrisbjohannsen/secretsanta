package net.orbitdev.secretsanta.app

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.db.InMemoryFamilyMemberStore
import net.orbitdev.secretsanta.db.InMemorySecretSantaStore
import net.orbitdev.secretsanta.engine.DefaultMatchEngine
import net.orbitdev.secretsanta.engine.IMatchEngine
import net.orbitdev.secretsanta.service.SecretSantaService

class SecretSantaApp {


    static void main(args) {

        def familyMemberStore = fillInMemoryStore(1000, new InMemoryFamilyMemberStore())
        def secretSantaStore = new InMemorySecretSantaStore()
        IMatchEngine engine = new DefaultMatchEngine()

        def secretSantaService = new SecretSantaService(familyMemberStore, secretSantaStore, engine)

        secretSantaService.generateMatches()

        println "${secretSantaStore.matches.size()} matches"
        secretSantaStore.matches.each { key, value ->
            println "Giver : ${key} \t Receiver : ${value}"

            assert secretSantaStore.matches.keySet().count { it == key } == 1
            assert secretSantaStore.matches.values().count { it == key } == 1
        }
    }

    static IFamilyMemberStore fillInMemoryStore(int numberOfMembers, IFamilyMemberStore store) {
        (1..numberOfMembers).each {
            store.addMember(new FamilyMember(name: "Member_${it}"))
        }

        assert store.members.size() == numberOfMembers
        store
    }
}
