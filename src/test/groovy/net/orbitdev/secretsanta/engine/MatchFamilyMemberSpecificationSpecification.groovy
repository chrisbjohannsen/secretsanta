package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import spock.lang.Specification

class MatchFamilyMemberSpecificationSpecification extends Specification {

    net.orbitdev.secretsanta.patterns.specification.Specification<FamilyMember> timeLimitSpec
    net.orbitdev.secretsanta.patterns.specification.Specification<FamilyMember> hasMatchSpec
    net.orbitdev.secretsanta.patterns.specification.Specification<FamilyMember> isFamilyMemberSpec
    net.orbitdev.secretsanta.patterns.specification.Specification<FamilyMember> matchSpec

    void setup(){
        timeLimitSpec = Mock()
        hasMatchSpec = Mock()
        isFamilyMemberSpec = Mock()
        matchSpec = new MatchFamilyMemberSpecification(timeLimitSpec, hasMatchSpec,isFamilyMemberSpec)
    }

    void "returns true when timeLimit is satisfied, hasMatch is not satisfied and isFamilyMember is not satisfied"() {
        setup:
        timeLimitSpec.isSatisfiedBy(_) >> true
        hasMatchSpec.isSatisfiedBy(_) >> false
        isFamilyMemberSpec.isSatisfiedBy(_) >> false

        when:
        def result = matchSpec.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        result
    }

    void "returns false when hasMatch is satisfied"() {
        setup:
        timeLimitSpec.isSatisfiedBy(_) >> true
        hasMatchSpec.isSatisfiedBy(_) >> true
        isFamilyMemberSpec.isSatisfiedBy(_) >> false

        when:
        def result = matchSpec.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }

    void "returns false when isFamilyMember is satisfied"() {
        setup:
        timeLimitSpec.isSatisfiedBy(_) >> true
        hasMatchSpec.isSatisfiedBy(_) >> false
        isFamilyMemberSpec.isSatisfiedBy(_) >> true

        when:
        def result = matchSpec.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }
    void "returns false when all specs satisfied"() {
        setup:
        timeLimitSpec.isSatisfiedBy(_) >> true
        hasMatchSpec.isSatisfiedBy(_) >> true
        isFamilyMemberSpec.isSatisfiedBy(_) >> true

        when:
        def result = matchSpec.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }


    void "returns false when all spec are not satisfied"() {
        setup:
        timeLimitSpec.isSatisfiedBy(_) >> false
        hasMatchSpec.isSatisfiedBy(_) >> false
        isFamilyMemberSpec.isSatisfiedBy(_) >> false

        when:
        def result = matchSpec.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }




    void "returns false when timeLimit & hasMatch not satisfied"() {
        setup:
        timeLimitSpec.isSatisfiedBy(_) >> false
        hasMatchSpec.isSatisfiedBy(_) >> false
        isFamilyMemberSpec.isSatisfiedBy(_) >> true

        when:
        def result = matchSpec.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }

    void "returns false when timeLimit & isFamily not satisfied"() {
        setup:
        timeLimitSpec.isSatisfiedBy(_) >> false
        hasMatchSpec.isSatisfiedBy(_) >> true
        isFamilyMemberSpec.isSatisfiedBy(_) >> false

        when:
        def result = matchSpec.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }


}
