package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

class IsImmediateFamilySpecification extends CompositeSpecification<FamilyMember>{
    FamilyMember target

    IsImmediateFamilySpecification(FamilyMember target) {
        this.target = target
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        if(target.immediateFamily.keySet().find{ it.id == familyMember.id }){
            return true
        }
        return false
    }
}