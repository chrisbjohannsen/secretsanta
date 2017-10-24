package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.Specification

class DefaultMatchEngine implements IMatchEngine {

    private Random random = new Random()
    private ISecretSantaStore store

    /**
     * This class implments the matching algorithm.
     * @param store
     */
    DefaultMatchEngine(ISecretSantaStore store) {
        this.store = store
    }

    /**
     * Picks a randomly selected family member from the list and validates the member against the santastore.
     * If it is already matched, removed it from the list and call recursively until a match is made or the list is
     * exhausted.
     * @param target
     * @param memberList
     * @param matchSpec
     * @return
     */
    @Override
    FamilyMember findMatch(FamilyMember target, List<FamilyMember> memberList, Specification<FamilyMember> matchSpec) {

        if (memberList.size() == 0) {
            return null
        }

        FamilyMember familyMember = memberList[random.nextInt(memberList.size())]

        if(matchSpec.isSatisfiedBy(familyMember)){
            return familyMember
        }

        memberList.removeAll { it.id == familyMember.id } //remove already tested from list
        findMatch(target, memberList, matchSpec)
    }
}
