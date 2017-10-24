package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

/**
 * Specification evaluates whether the FamilyMember's last match as a giver was at least 3 years prior to today's date
 */
class LastGiverMatchThreeYearsOrGreaterSpecification extends CompositeSpecification<FamilyMember> {

    private ISecretSantaStore store
    private FamilyMember toMatch

    /**
     * Constructor
     * @param store A ISecretSantaStore
     * @param toMatch a family member in the receiver role
     */
    LastGiverMatchThreeYearsOrGreaterSpecification(ISecretSantaStore store, FamilyMember toMatch) {
        this.toMatch = toMatch
        this.store = store
    }

    /**
     * Test a family member to ensure it hasn't been matched to target within the last 3 years from the current date.
     * @param familyMember as giver
     * @return True if match age is greater than or equal to 3 years or not matched
    */
    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {

        SecretSantaMatch[] matches = this.store.getMatches(familyMember.id)
        SecretSantaMatch giverMatch = matches?.sort{ it.matchDate }?.reverse() //sort by matchDate desc
                .find{ it.giver.id == familyMember.id && it.receiver.id == toMatch.id } //return the first match

        if(giverMatch){
            return ChronoUnit.YEARS.between(giverMatch.matchDate, ZonedDateTime.now(ZoneOffset.UTC)) >= 3
        }
        return true
    }
}
