//
//  LaunchViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 08/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "LaunchViewController.h"
#import "LoginViewController.h"
#import "RegisterViewController.h"
#import "Constants.h"

@interface LaunchViewController () {
    BOOL isLayoutCompleted;
}

@end

@implementation LaunchViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = BG_COLOR;
    isLayoutCompleted = NO;
}
- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    if (!isLayoutCompleted) {
        [self prepareSubviews];
        isLayoutCompleted = YES;
    }
    self.title = @"";
}

- (void)prepareSubviews {
    CGRect frame = self.view.frame;
    UILabel *title = [[UILabel alloc] initWithFrame:CGRectMake(frame.size.width/2 - 75, 80, 150, 30)];
    title.textAlignment = NSTextAlignmentCenter;
    title.font = [UIFont boldSystemFontOfSize:24];
    title.text = @"Clubhouse";
    [self.view addSubview:title];
    
    UILabel *desc = [[UILabel alloc] initWithFrame:CGRectMake(15 , CGRectGetMaxY(title.frame) + 30, frame.size.width - 30, 100)];
    desc.textAlignment = NSTextAlignmentCenter;
    //title.font = [UIFont boldSystemFontOfSize:24];
    desc.text = @"This is the place to find those unique things you've always wanted and get to know those unique people who have them.";
    [self.view addSubview:desc];
    desc.numberOfLines = 5;
    
    UIButton *login = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [login setTitle:@"Sign In" forState:UIControlStateNormal];
    login.frame = CGRectMake(frame.size.width/2 - 75, CGRectGetMaxY(desc.frame) + 30, 150, 30);
    [login addTarget:self action:@selector(loginClicked) forControlEvents:UIControlEventTouchUpInside];
    [login setBackgroundColor:[UIColor whiteColor]];
    [login setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [self.view addSubview:login];
    
    
    UIButton *registration = [UIButton buttonWithType:UIButtonTypeRoundedRect];
   // [registration setTitle:@"Register" forState:UIControlStateNormal];
    registration.frame = CGRectMake(frame.size.width/2 - 75, CGRectGetMaxY(login.frame) + 30, 150, 30);
    [registration setTitle:@"Sign Up" forState:UIControlStateNormal];
    [registration setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [registration setBackgroundColor:[UIColor greenColor]];
    [registration addTarget:self action:@selector(registerClicked) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:registration];
}

- (void)loginClicked {
    LoginViewController *l = [[LoginViewController alloc] init];
    [self.navigationController pushViewController:l animated:YES];
}
- (void)registerClicked {
    RegisterViewController *r = [[RegisterViewController alloc] init];
    [self.navigationController pushViewController:r animated:YES];
}

@end
