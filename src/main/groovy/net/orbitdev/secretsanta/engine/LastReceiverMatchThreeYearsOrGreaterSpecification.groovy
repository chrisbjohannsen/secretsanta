package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

/**
 * Specification evaluates whether the FamilyMember's last match as a receiver was at least 3 years prior to today's date
 */
class LastReceiverMatchThreeYearsOrGreaterSpecification extends CompositeSpecification<FamilyMember>{

    private ISecretSantaStore store
    private FamilyMember toMatch

    /**
     * Constructor
     * @param store a ISecretSantaStore
     * @param toMatch toMatch a family member in the giver role
     */
    LastReceiverMatchThreeYearsOrGreaterSpecification(ISecretSantaStore store, FamilyMember toMatch) {
        this.toMatch = toMatch
        this.store = store
    }

    /**
     * Test a family member to ensure it hasn't been matched to target within the last 3 years from the current date.
     * @param familyMember as receiver
     * @return True if match age is greater than or equal to 3 years or not matched
     */
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
