//
//  PlanetDetailScreen.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import Foundation
import SwiftUI
import shared

struct PlanetDetailScreen: View {
    
    private var uid: String
    
    init(uid: String) {
        self.uid = uid
    }
    
    var body: some View {
        Text("Detail Screen : \(uid)")
    }
    
}
