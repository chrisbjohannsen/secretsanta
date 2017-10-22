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

    LastGiverMatchThreeYearsOrGreaterSpecification(Specification<FamilyMember> lastReceivedSpec) {
        this.lastReceivedSpec = lastReceivedSpec
    }

    LastGiverMatchThreeYearsOrGreaterSpecification(ISecretSantaStore store) {
        this.store = store
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {

        SecretSantaMatch[] matches = this.store.getMatches(familyMember.id)
        SecretSantaMatch giverMatch = matches.find{ it.giver.id == familyMember.id }
        if(giverMatch){
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)

            return ChronoUnit.YEARS.between(giverMatch.matchDate, now) >= 3
        }
        return false
    }
}
