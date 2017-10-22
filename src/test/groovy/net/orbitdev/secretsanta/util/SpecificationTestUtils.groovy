package net.orbitdev.secretsanta.util

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class SpecificationTestUtils {

    static Map mockSecretSantaMatchesData() {
        def member = new FamilyMember(id: 0, name: 'Demelza')
        def member1 = new FamilyMember(id: 1, name: 'Francis')
        def member2 = new FamilyMember(id: 2, name: 'Ross')
        def member3 = new FamilyMember(id: 3, name: 'Henry-Charles')
        def member4 = new FamilyMember(id: 4, name: 'Elizabeth')
        def member5 = new FamilyMember(id: 5, name: 'Ennis')
        def member6 = new FamilyMember(id: 6, name: 'George')
        def member7 = new FamilyMember(id: 7, name: 'Caroline')

        def matches = [0: [new SecretSantaMatch(giver: member, receiver: member7, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                       new SecretSantaMatch(giver: member6, receiver: member, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                   1: [new SecretSantaMatch(giver: member2, receiver: member4, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                       new SecretSantaMatch(giver: member1, receiver: member2, matchDate: ZonedDateTime.of(2015, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                   3: [new SecretSantaMatch(giver: member3, receiver: member6, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                       new SecretSantaMatch(giver: member7, receiver: member3, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                   4: [new SecretSantaMatch(giver: member4, receiver: member5, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                       new SecretSantaMatch(giver: member2, receiver: member4, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                   5: [new SecretSantaMatch(giver: member5, receiver: member1, matchDate: ZonedDateTime.of(LocalDateTime.now().minusYears(3), ZoneId.of("UTC"))),
                       new SecretSantaMatch(giver: member4, receiver: member5, matchDate: ZonedDateTime.of(LocalDateTime.now().minusYears(3), ZoneId.of("UTC")))],
                   6: [new SecretSantaMatch(giver: member6, receiver: member, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                       new SecretSantaMatch(giver: member3, receiver: member6, matchDate: ZonedDateTime.of(2011, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))],
                   7: [new SecretSantaMatch(giver: member7, receiver: member3, matchDate: ZonedDateTime.of(2017, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC"))),
                       new SecretSantaMatch(giver: member, receiver: member7, matchDate: ZonedDateTime.of(2017, 11, 2, 0, 0, 0, 0, ZoneId.of("UTC")))]

        ]
        matches
    }
}
