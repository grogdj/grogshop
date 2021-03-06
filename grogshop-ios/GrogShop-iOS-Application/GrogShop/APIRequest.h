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
                              failureBlock:(void (^)(NSError *e))failed;

- (void)startGetRequestWithSuccessBlock:(void (^)(id rootObj))success
                           failureBlock:(void (^)(NSError *e))failed extension:(NSString *)pathExtension public:(BOOL)publicAPI;

- (void)startUpdateUserProfileRequestWithSuccessBlock:(void (^)(id rootObj))success
                                         failureBlock:(void (^)(NSError *e))failed name:(NSString *)name location:(NSString *)location bio:(NSString *)bio;
@end
