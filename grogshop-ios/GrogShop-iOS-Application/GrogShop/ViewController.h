//
//  ViewController.h
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController<UITextFieldDelegate>

@property(strong,retain) UITextField *email,*password;
@property(strong,retain) UIButton *login,*registerUser;

@end

