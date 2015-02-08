//
//  RegisterViewController.h
//  GrogShop
//
//  Created by Nikita Pahadia on 08/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RegisterViewController : UIViewController<UITextFieldDelegate>

@property(strong,retain) UITextField *email,*password,*repeatp;
@property(strong,retain) UIButton *registerUser;
@end
