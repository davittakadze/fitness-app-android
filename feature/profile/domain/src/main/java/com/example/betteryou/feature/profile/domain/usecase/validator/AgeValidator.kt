package com.example.betteryou.feature.profile.domain.usecase.validator

import javax.inject.Inject

class AgeValidatorUseCase @Inject constructor() {
    operator fun invoke(age:Int): Boolean {
        return !(age<=0||age>=150)
    }
}