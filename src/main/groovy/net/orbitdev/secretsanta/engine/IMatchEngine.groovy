package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.Specification

/**
 * Defines the contract for the match algorithm
 */
interface IMatchEngine {

    FamilyMember findMatch(FamilyMember matchFor,
                           MatchType matchType,
                           List<FamilyMember> memberList,
                           Specification<FamilyMember> timeLimitSpec,
                           Specification<FamilyMember> isFamilyMemberSpec)
}