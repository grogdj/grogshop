//
//  User.h
//  GrogShop
//
//  Created by Rishabh Chowdhary on 10/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface User : NSObject<NSCoding>
@property (nonatomic, retain) NSString *emailId,*authToken,*name,*location,*bio;
@end
