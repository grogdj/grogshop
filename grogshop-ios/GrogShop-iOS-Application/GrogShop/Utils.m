//
//  Utils.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "Utils.h"

@implementation Utils

+ (NSString *)getURLEncoded:(NSString*) unencodedString {
    if (!unencodedString) {
        return nil;
    }
    NSString *encodedString = (NSString *)CFBridgingRelease(CFURLCreateStringByAddingPercentEscapes(NULL,(CFStringRef)unencodedString,NULL,(CFStringRef)@"!*'();:@&=+-$,/?\"%#<>|^~`{}\\[ ]",kCFStringEncodingUTF8));
    return encodedString;
}

+ (BOOL)isEmpty:(NSString *)input {
    if(input == NULL) return true;
    if ([[input stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] isEqualToString:@""]) {
        return true;
    }
    return false;
}

+(BOOL)isEmailAddressValid:(NSString *)input {
    if ([Utils isEmpty:input]) return false;
    BOOL stricterFilter = NO; // Discussion http://blog.logichigh.com/2010/09/02/validating-an-e-mail-address/
    NSString *stricterFilterString = @"[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}";
    NSString *laxString = @".+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2}[A-Za-z]*";
    NSString *emailRegex = stricterFilter ? stricterFilterString : laxString;
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    return [emailTest evaluateWithObject:input];
}
@end
