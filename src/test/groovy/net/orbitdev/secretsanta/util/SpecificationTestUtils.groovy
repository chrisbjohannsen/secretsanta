package net.orbitdev.secretsanta.util

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class SpecificationTestUtils {

    static Map mockSecretSantaMatchesData() {
        def members = mockFamilyMembers()
        def member  = members[0]
        def member1 = members[1]
        def member2 = members[2]
        def member3 = members[3]
        def member4 = members[4]
        def member5 = members[5]
        def member6 = members[6]
        def member7 = members[7]

        def matches = [
                0: [
                        new SecretSantaMatch(giver: member, receiver: member7, matchDate: ZonedDateTime.of(2017, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member6, receiver: member, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                1: [
                        new SecretSantaMatch(giver: member1, receiver: member4, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member5, receiver: member1, matchDate: ZonedDateTime.of(LocalDateTime.now().minusYears(3), ZoneId.of("UTC")))],
                2: [
                        new SecretSantaMatch(giver: member2, receiver: member4, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member5, receiver: member2, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                3: [
                        new SecretSantaMatch(giver: member3, receiver: member6, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member7, receiver: member3, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member7, receiver: member3, matchDate: ZonedDateTime.of(2016, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                4: [
                        new SecretSantaMatch(giver: member4, receiver: member5, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member4, receiver: member5, matchDate: ZonedDateTime.of(2016, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member1, receiver: member4, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member2, receiver: member4, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                5: [
                        new SecretSantaMatch(giver: member5, receiver: member1, matchDate: ZonedDateTime.of(LocalDateTime.now().minusYears(3), ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member4, receiver: member5, matchDate: ZonedDateTime.of(2016, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member4, receiver: member5, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member5, receiver: member2, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                6: [
                        new SecretSantaMatch(giver: member6, receiver: member, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member3, receiver: member6, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                7: [
                        new SecretSantaMatch(giver: member7, receiver: member3, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                        new SecretSantaMatch(giver: member, receiver: member7, matchDate: ZonedDateTime.of(2017, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))]

        ]
        matches
    }

    static  mockFamilyMembers(){
        return [new FamilyMember(id: 0, name: 'Demelza'),
                new FamilyMember(id: 1, name: 'Francis'),
                new FamilyMember(id: 2, name: 'Ross'),
                new FamilyMember(id: 3, name: 'Henry-Charles'),
                new FamilyMember(id: 4, name: 'Elizabeth'),
                new FamilyMember(id: 5, name: 'Ennis'),
                new FamilyMember(id: 6, name: 'George'),
                new FamilyMember(id: 7, name: 'Caroline')]
    }
}
