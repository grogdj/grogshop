//
//  SettingsViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "SettingsViewController.h"

@implementation SettingsViewController

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self.tabBarItem setTitle:@"Settings"];
    }
    return self;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor]; 
}

@end
