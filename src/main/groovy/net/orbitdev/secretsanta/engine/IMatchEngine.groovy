package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import net.orbitdev.secretsanta.patterns.specification.Specification

/**
 * Defines the contract for the match algorithm
 */
interface IMatchEngine {

    @Deprecated
    FamilyMember findMatch(FamilyMember matchFor,
                           MatchType matchType,
                           List<FamilyMember> memberList,
                           Specification<FamilyMember> timeLimitSpec,
                           Specification<FamilyMember> isFamilyMemberSpec)

    FamilyMember findMatch(FamilyMember target, List<FamilyMember> memberList, CompositeSpecification<FamilyMember> matchSpec)
}