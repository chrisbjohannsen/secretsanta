package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember

interface IMatchEngine {

    FamilyMember findMatch(FamilyMember matchFor, MatchType matchType, List<FamilyMember> memberList)
}