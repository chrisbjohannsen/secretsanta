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
    private FamilyMember toMatch

    LastReceiverMatchThreeYearsOrGreaterSpecification(ISecretSantaStore store, FamilyMember toMatch) {
        this.toMatch = toMatch
        this.store = store
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        SecretSantaMatch[] matches = this.store.getMatches(familyMember.id)
        SecretSantaMatch receiverMatch = matches?.sort{ it.matchDate }?.reverse()
                .find{ it.receiver.id == familyMember.id && it.giver.id == toMatch.id}
        if(receiverMatch){
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)

            return ChronoUnit.YEARS.between(receiverMatch.matchDate, now) >= 3
        }
        return true
    }
}
