//
//  Utils.h
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Utils : NSObject

+ (BOOL)isEmpty:(NSString *)input;
+(BOOL)isEmailAddressValid:(NSString *)input;
+ (NSString *)getURLEncoded:(NSString*) unencodedString;
@end
