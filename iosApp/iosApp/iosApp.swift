//
//  iosAppApp.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import SwiftUI
import FlowStacks
import shared

@main
struct iosApp: App {
    
    @State private var routes: Routes<AppRoute> = []
    
    init() {
        KoinInitializerKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            PlanetListScreen()
                .showing($routes, embedInNavigationView: true) { route, _ in
                    route.getView()
                        .hideNavigationBar()
                }
        }
    }
}
