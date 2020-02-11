package com.yxh.miaosha.validator;

import com.yxh.miaosha.util.ValidatoUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * @author galaxy
 * @date 20-2-11 - 上午11:03
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required){
            return ValidatoUtil.isMobile(value);
        }else {
            if (StringUtils.isEmpty(value)){
                return true;
            }else {
                return ValidatoUtil.isMobile(value);
            }
        }
    }
}
