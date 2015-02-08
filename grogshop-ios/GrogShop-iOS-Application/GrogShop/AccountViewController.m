//
//  AccountViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "AccountViewController.h"

@implementation AccountViewController

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self.tabBarItem setTitle:@"Account"];
    }
    return self;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor greenColor];
}


@end
