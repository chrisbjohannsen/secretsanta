package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

/**
 * Specification evaluates if a member is in another members immediate family
 */
class IsImmediateFamilySpecification extends CompositeSpecification<FamilyMember>{
    FamilyMember target

    /**
     * Constructor
     * @param target Family member
     */
    IsImmediateFamilySpecification(FamilyMember target) {
        this.target = target
    }

    /**
     * Test whether a family member is part of the target member's immediate family
     * @param familyMember
     * @return True when member is in the immediate family
     */
    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        if(target.immediateFamily.keySet().find{ it.id == familyMember.id }){
            return true
        }
        return false
    }
}
