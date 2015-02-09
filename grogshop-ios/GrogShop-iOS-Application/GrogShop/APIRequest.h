//
//  APIRequest.h
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Constants.h"

@interface APIRequest : NSObject

- (void)startRegistrationRequestWithSuccessBlock:(void (^)(id rootObj))success
                                    failureBlock:(void (^)(NSError *e))failed email:(NSString *)email password:(NSString *)password;

- (void)startLoginRequestWithSuccessBlock:(void (^)(id rootObj))success
                             failureBlock:(void (^)(NSError *e))failed email:(NSString *)email password:(NSString *)password;

- (void)startLogoutRequestWithSuccessBlock:(void (^)(id rootObj))success
                              failureBlock:(void (^)(NSError *e))failed email:(NSString *)email authToken:(NSString *)token;

- (void)startGetRequestWithSuccessBlock:(void (^)(id rootObj))success
                           failureBlock:(void (^)(NSError *e))failed extension:(NSString *)pathExtension;

@end
