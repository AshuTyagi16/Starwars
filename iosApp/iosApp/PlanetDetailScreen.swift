//
//  PlanetDetailScreen.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import Foundation
import SwiftUI
import FlowStacks
import ProgressIndicatorView
import shared

struct PlanetDetailScreen: View {
    
    @EnvironmentObject private var navigator: FlowNavigator<AppRoute>
    
    @State private var progressForDefaultSector: CGFloat = 0.0
    
    private let timer = Timer.publish(every: 1 / 15, on: .main, in: .common).autoconnect()
    
    @State private var viewModel: PlanetDetailScreenModel?
    
    @State private var planetDetail: PlanetDetail?
    
    @State private var isLoading: Bool = true
    
    private var uid: String
    
    init(uid: String) {
        self.uid = uid
    }
    
    var body: some View {
        VStack {
            HStack {
                Image(systemName: "arrow.left")
                    .frame(width: 16, height: 16)
                    .foregroundColor(Color.white)
                    .onTapGesture {
                        navigator.goBack()
                    }
                
                Text(planetDetail?.properties.name ?? "")
                    .foregroundColor(Color.white)
                    .font(.system(size:16, weight: .semibold))
                    .bold()
                    .padding(.horizontal, 12)
                Spacer()
            }
            .padding(.all, 12)
            List {
                if(isLoading) {
                    VStack(alignment: .center) {
                        Spacer()
                            .frame(height: 300)
                        ProgressIndicatorView(
                            isVisible: .constant(true),
                            type: .default(progress: $progressForDefaultSector)
                        )
                        .frame(width: 20.0, height: 20.0)
                        .foregroundColor(.white)
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
                    .listRowInsets(EdgeInsets())
                    .listRowSeparator(.hidden, edges: .all)
                    .listRowBackground(Color.clear)
                } else if(planetDetail != nil){
                    Text("Planet Name : \(planetDetail?.properties.name ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Climate : \(planetDetail?.properties.climate ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Diameter : \(planetDetail?.properties.diameter ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Gravity : \(planetDetail?.properties.gravity ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Orbital Period : \(planetDetail?.properties.orbitalPeriod ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Population : \(planetDetail?.properties.population ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Rotation Period : \(planetDetail?.properties.rotationPeriod ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Surface Water : \(planetDetail?.properties.surfaceWater ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                    
                    Text("Terrain : \(planetDetail?.properties.terrain ?? "")")
                        .font(.title)
                        .fontDesign(.rounded)
                        .fontWeight(.bold)
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden, edges: .all)
                        .listRowBackground(Color.clear)
                }
            }
            .listStyle(.plain)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .background(Color.black.opacity(0.92)).edgesIgnoringSafeArea(.bottom)
        .task {
            let viewModel = SharedModuleDependencies.shared.planetDetailScreenModel
            await withTaskCancellationHandler(
                operation: {
                    self.viewModel = viewModel
                    self.viewModel?.fetchPlanetDetail(uid: self.uid)
                },
                onCancel: {
                    viewModel.onCleared()
                    DispatchQueue.main.async {
                        self.viewModel = nil
                    }
                }
            )
        }
        .task {
            if(viewModel != nil){
                for await planetDetailResponse in viewModel!.planetDetailResponse {
                    switch onEnum(of: planetDetailResponse) {
                    case .data:
                        self.isLoading = false
                        self.planetDetail = planetDetailResponse?.dataOrNull()
                        break
                    case .loading(let loading):
                        self.isLoading = true
                        break
                    default:
                        break
                    }
                }
            }
        }
        .onReceive(timer) { _ in
            if progressForDefaultSector >= 1.5 {
                progressForDefaultSector = 0
            } else {
                progressForDefaultSector += 1 / 10
            }
        }
    }
    
}
