//
//  SettingsViewController.h
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SettingsViewController : UIViewController <UITextFieldDelegate>

@property (nonatomic, retain) UITextField *username,*location;
@property (nonatomic, retain) UITextField *bio;
@property (nonatomic, retain) UIButton *save;
@end
