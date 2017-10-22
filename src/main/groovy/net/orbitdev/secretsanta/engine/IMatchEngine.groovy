package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember

/**
 * Defines the contract for the match algorithm
 */
interface IMatchEngine {

    FamilyMember findMatch(FamilyMember matchFor, MatchType matchType, List<FamilyMember> memberList)
}