package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.ISecretSantaStore
import spock.lang.Specification

class SecretSantaServiceTest extends Specification {

    SecretSantaService service
    IFamilyMemberStore familyStore
    ISecretSantaStore santaStore
    FamilyMember[] members
    Map matches = [:]

    void setup() {
        members = [
                new FamilyMember(name: 'Johnny'),
                new FamilyMember(name: 'Cory'),
                new FamilyMember(name: 'Randi'),
                new FamilyMember(name: 'Erin'),
                new FamilyMember(name: 'Susan'),
                new FamilyMember(name: 'Ron'),
                new FamilyMember(name: 'Chris'),
                new FamilyMember(name: 'Miranda')
        ]
        familyStore = Mock(IFamilyMemberStore)
        familyStore.getMembers() >> members
        familyStore.getRandomMember() >> {
            members[new Random().nextInt(members.size())]
        }

        santaStore = Mock(ISecretSantaStore)
        santaStore.addMatch() >> { giver, receiver ->
            this.matches.put(giver, receiver)
        }
        santaStore.hasGiver() >> { receiver ->
            return matches.containsValue(receiver)
        }
        santaStore.hasReceiver() >> { giver ->
            return matches.containsKey(giver)
        }

        service = new SecretSantaService(familyStore, santaStore)
    }

    void "constructor sets up the service"() {
        expect:
        service.familyMemberStore == familyStore
        service.matchStore == santaStore
    }

    void "checks to see if member already matched"() {
        setup:
        def name = 'Dennis'

        when:
        service.hasMatch(name, SecretSantaService.MATCH_TYPE.GIVER)

        then:
        1 * santaStore.hasReceiver(name)

        when:
        service.hasMatch(name, SecretSantaService.MATCH_TYPE.RECEIVER)

        then:
        1 * santaStore.hasGiver(name)
    }

    void "match cannot be self"() {

        expect:
        assert service.makeMatch(name) != name

        where:
        name << [
                'Chris',
                'Randi',
                'Johnny',
                'Cory',
                'Erin',
                'Ron',
                'Susan'
        ]

    }

    void "matches get stored in santa store"() {
        when:
        service.generateMatches()

        then:
        16 * santaStore.addMatch(_,_)

    }
}
