package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.engine.IMatchEngine
import net.orbitdev.secretsanta.engine.MatchType
import spock.lang.Shared
import spock.lang.Specification

class SecretSantaServiceTest extends Specification {

    SecretSantaService service
    IFamilyMemberStore familyStore
    ISecretSantaStore santaStore
    IMatchEngine engine

    @Shared
    FamilyMember[] members


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
            if(members.size() > 0) {
                return members[new Random().nextInt(members.size())]
            }
            return null
        }

        santaStore = Mock(ISecretSantaStore)
        engine = Mock(IMatchEngine)
        service = new SecretSantaService(familyStore, santaStore, engine)
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
        service.hasMatch(name, MatchType.GIVER)

        then:
        1 * santaStore.hasReceiver(name)

        when:
        service.hasMatch(name, MatchType.RECEIVER)

        then:
        1 * santaStore.hasGiver(name)
    }

    void "match cannot be self"() {

        expect:
        assert service.makeMatch(member,MatchType.GIVER) != member
        assert service.makeMatch(member,MatchType.RECEIVER) != member

        where:
        member << members

    }

    void "matches get stored in santa store"() {
        when:
        service.generateMatches()

        then:
        16 * santaStore.addMatch(_,_)

    }

//    void "handles 0 family members"(){
//        setup:
//        members = []
//
//        when:
//        service.generateMatches()
//
//        then:
//        thrown(Exception)
//    }
//
//    void "handles null family member"(){
//        setup:
//        def fstore = Mock(IFamilyMemberStore)
//        fstore.getMembers()>> members
//        fstore.getRandomMember() >> null
//
//        def sservice = new SecretSantaService(fstore,santaStore, engine)
//
//        when:
//        sservice.generateMatches()
//
//        then:
//        thrown(Exception)
//    }
}
