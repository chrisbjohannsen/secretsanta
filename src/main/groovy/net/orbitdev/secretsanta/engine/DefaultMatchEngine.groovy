package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember

class DefaultMatchEngine implements IMatchEngine {

    private Random randomizer

    DefaultMatchEngine() {
        this.randomizer = new Random()
    }

    @Override
    FamilyMember findMatch(FamilyMember matchFor, MatchType matchType, List<FamilyMember> memberList) {
        FamilyMember random = memberList[randomizer.nextInt(memberList.size())]
        if(!random){
            throw new Exception("No Members in store")
        }

        if(!hasMatch(random.name,matchType)) {
            return random.name
        }

        memberList.removeAll{ it.name == random.name } //remove already tested from list
        findMatch(matchFor, matchType, memberList) //call findMatch until we find an unmatched name
    }
}
