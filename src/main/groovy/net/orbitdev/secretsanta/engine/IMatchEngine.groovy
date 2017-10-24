package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.Specification

/**
 * Defines the contract for the match algorithm
 */
interface IMatchEngine {

    /**
     * Find a match for the target member from the list of members using the rules in the match spec.
     * @param target
     * @param memberList
     * @param matchSpec
     * @return Matching FamilyMember
     */
    FamilyMember findMatch(FamilyMember target, List<FamilyMember> memberList, Specification<FamilyMember> matchSpec)
}