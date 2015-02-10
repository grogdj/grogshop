//
//  User.m
//  GrogShop
//
//  Created by Rishabh Chowdhary on 10/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "User.h"

@implementation User

- (id)initWithCoder:(NSCoder *)aDecoder {
    self.emailId = [aDecoder decodeObjectForKey:@"emailId"];
    self.authToken = [aDecoder decodeObjectForKey:@"authToken"];
    self.name = [aDecoder decodeObjectForKey:@"name"];
    self.location = [aDecoder decodeObjectForKey:@"location"];
    self.bio = [aDecoder decodeObjectForKey:@"bio"];
    return self;
}

- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:self.emailId forKey:@"emailId"];
    [aCoder encodeObject:self.name forKey:@"name"];
    [aCoder encodeObject:self.authToken forKey:@"authToken"];
    [aCoder encodeObject:self.location forKey:@"location"];
    [aCoder encodeObject:self.bio forKey:@"bio"];
}

@end
