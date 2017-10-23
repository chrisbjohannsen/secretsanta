package net.orbitdev.secretsanta.util

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class SpecificationTestUtils {

    static Map mockSecretSantaMatchesData() {
        def members = mockFamilyMembers()
        def member = members[0]
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

    static mockFamilyMembers() {
        def members = [new FamilyMember(id: 0, name: 'Demelza Poldark'),
                       new FamilyMember(id: 1, name: 'Francis Poldark'),
                       new FamilyMember(id: 2, name: 'Ross Poldark'),
                       new FamilyMember(id: 3, name: 'Geoffery-Charles Poldark'),
                       new FamilyMember(id: 4, name: 'Elizabeth Warleggan'),
                       new FamilyMember(id: 5, name: 'Dwight Enys'),
                       new FamilyMember(id: 6, name: 'George Warleggan'),
                       new FamilyMember(id: 7, name: 'Caroline Penvenen'),
                       new FamilyMember(id: 8, name: 'Tom Carne'),
                       new FamilyMember(id: 9, name: 'Drake Carne'),
                       new FamilyMember(id: 10, name: 'Julia Poldark'),
                       new FamilyMember(id: 11, name: 'Jeremy Poldark')

        ]

        members[0].immediateFamily.put(members[2], 'spouse')
        members[0].immediateFamily.put(members[10], 'child')
        members[0].immediateFamily.put(members[11], 'child')
        members[0].immediateFamily.put(members[8], 'parent')

        members[1].immediateFamily.put(members[4], 'spouse')
        members[1].immediateFamily.put(members[3], 'child')

        members[2].immediateFamily.put(members[0], 'spouse')
        members[2].immediateFamily.put(members[10], 'child')
        members[2].immediateFamily.put(members[11], 'child')

        members[3].immediateFamily.put(members[1], 'parent')
        members[3].immediateFamily.put(members[4], 'parent')

        members[4].immediateFamily.put(members[3], 'child')
        members[4].immediateFamily.put(members[1], 'spouse')
        members[4].immediateFamily.put(members[6], 'spouse')

        members[5].immediateFamily.put(members[7], 'spouse')

        members[6].immediateFamily.put(members[4], 'spouse')

        members[7].immediateFamily.put(members[5], 'spouse')

        members[8].immediateFamily.put(members[0], 'child')
        members[8].immediateFamily.put(members[9], 'parent')

        members[9].immediateFamily.put(members[8], 'child')

        members[10].immediateFamily.put(members[0], 'parent')
        members[10].immediateFamily.put(members[2], 'parent')

        members[11].immediateFamily.put(members[2], 'parent')
        members[11].immediateFamily.put(members[0], 'parent')

        members
    }

}
