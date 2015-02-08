//
//  HomeViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "HomeViewController.h"

@implementation HomeViewController

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self.tabBarItem setTitle:@"Home"];
        self.navigationController.navigationBarHidden = YES;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
}

@end
