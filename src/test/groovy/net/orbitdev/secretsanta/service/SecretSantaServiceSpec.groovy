package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.engine.IMatchEngine
import net.orbitdev.secretsanta.engine.MatchType
import spock.lang.Shared
import spock.lang.Specification

class SecretSantaServiceSpec extends Specification {

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

        santaStore = Mock(ISecretSantaStore)gst
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
        def member = new FamilyMember(id:0,name:'Dennis')

        when:
        service.hasMatch(member, MatchType.GIVER)

        then:
        1 * santaStore.isGiver(member)

        when:
        service.hasMatch(member, MatchType.RECEIVER)

        then:
        1 * santaStore.isReceiver(member)
    }

    void "match cannot be self"() {

        expect:
        assert service.makeMatch(member,MatchType.GIVER) != member
        assert service.makeMatch(member,MatchType.RECEIVER) != member

        where:
        member << members

    }

    void "all members are checked for both match types matched"() {
        when:
        service.generateMatches()

        then:
        members.size() * engine.findMatch(_,MatchType.RECEIVER,_,_)
        members.size() * engine.findMatch(_,MatchType.GIVER,_,_)
    }

}
