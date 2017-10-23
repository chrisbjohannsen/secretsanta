package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import net.orbitdev.secretsanta.patterns.specification.Specification
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


/**
 * Specification evaluates whether the FamilyMember's last match was at least 3 years prior to todays date
 */
class LastGiverMatchThreeYearsOrGreaterSpecification extends CompositeSpecification<FamilyMember> {

    private Specification<FamilyMember> lastReceivedSpec
    private ISecretSantaStore store
    private FamilyMember toMatch

    LastGiverMatchThreeYearsOrGreaterSpecification(Specification<FamilyMember> lastReceivedSpec) {
        this.lastReceivedSpec = lastReceivedSpec
    }

    LastGiverMatchThreeYearsOrGreaterSpecification(ISecretSantaStore store, FamilyMember toMatch) {
        this.toMatch = toMatch
        this.store = store
    }

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
