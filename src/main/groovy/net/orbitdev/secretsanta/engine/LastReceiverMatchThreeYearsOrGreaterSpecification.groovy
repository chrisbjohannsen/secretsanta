package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class LastReceiverMatchThreeYearsOrGreaterSpecification extends CompositeSpecification<FamilyMember>{

    private ISecretSantaStore store

    LastReceiverMatchThreeYearsOrGreaterSpecification(ISecretSantaStore store) {
        this.store = store
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        SecretSantaMatch[] matches = this.store.getMatches(familyMember.id)
        SecretSantaMatch receiverMatch = matches.find{ it.receiver.id == familyMember.id }
        if(receiverMatch){
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)

            return ChronoUnit.YEARS.between(receiverMatch.matchDate, now) >= 3
        }
    }
}
