import { useLocation, useNavigate } from 'react-router-dom';
import { ArrowLeft, Award, AlertTriangle, CheckCircle, Calendar, FileText } from 'lucide-react';
import { RadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis, Radar, ResponsiveContainer } from 'recharts';

export default function DashboardPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const data = location.state?.data;

    // Debug: log the data to console
    console.log('Dashboard received data:', data);

    if (!data) {
        console.error('No data received, redirecting to home');
        navigate('/');
        return null;
    }

    // Safely access nested properties with fallbacks
    const metrics = data.profileMetrics || {};
    const scoreBreakdown = metrics.scoreBreakdown || {};
    const activityMetrics = metrics.activityMetrics || {};

    const radarData = [
        { metric: 'Documentation', score: scoreBreakdown.documentationQuality || 0 },
        { metric: 'Code Quality', score: scoreBreakdown.codeStructure || 0 },
        { metric: 'Activity', score: scoreBreakdown.activityConsistency || 0 },
        { metric: 'Organization', score: scoreBreakdown.repositoryOrganization || 0 },
        { metric: 'Impact', score: scoreBreakdown.projectImpact || 0 },
        { metric: 'Tech Depth', score: scoreBreakdown.technicalDepth || 0 },
    ];

    const getScoreColor = (score) => {
        if (score >= 80) return 'text-green-600';
        if (score >= 60) return 'text-blue-600';
        if (score >= 40) return 'text-yellow-600';
        return 'text-red-600';
    };

    const getSeverityColor = (severity) => {
        if (severity === 'High') return 'bg-red-100 text-red-800 border-red-300';
        if (severity === 'Medium') return 'bg-yellow-100 text-yellow-800 border-yellow-300';
        return 'bg-blue-100 text-blue-800 border-blue-300';
    };

    return (
        <div className="min-h-screen bg-gray-50 pb-12">
            {/* Header */}
            <div className="bg-white border-b border-gray-200">
                <div className="max-w-7xl mx-auto px-6 py-6">
                    <button
                        onClick={() => navigate('/')}
                        className="flex items-center gap-2 text-gray-600 hover:text-gray-900 mb-4 transition-colors"
                    >
                        <ArrowLeft size={20} />
                        <span>Back to Home</span>
                    </button>

                    <div className="flex items-center justify-between">
                        <div>
                            <h1 className="text-3xl font-bold text-gray-900">
                                {metrics.username || 'GitHub'} Portfolio Analysis
                            </h1>
                            <p className="text-gray-600 mt-1">
                                {activityMetrics.publicRepositories || 0} public repositories
                            </p>
                        </div>
                        <div className="text-center">
                            <div className="text-6xl font-bold text-white mb-2">
                                {metrics.overallScore || 0}
                            </div>
                            <div className="text-sm text-gray-500 mt-1">Grade: {metrics.grade || 'N/A'}</div>

                            {/* Confidence Badge */}
                            {data.confidenceLevel && (
                                <div className={`mt-3 inline-block px-4 py-1 rounded-full text-sm font-bold ${data.confidenceLevel === 'High' ? 'bg-green-500 text-white' :
                                    data.confidenceLevel === 'Moderate' ? 'bg-yellow-500 text-white' :
                                        'bg-red-500 text-white'
                                    }`}>
                                    {data.confidenceLevel === 'High' ? '🟢' : data.confidenceLevel === 'Moderate' ? '🟡' : '🔴'} {data.confidenceLevel} Confidence
                                </div>
                            )}

                            {/* Flagship Project */}
                            {data.flagshipProject && (
                                <div className="mt-2 text-sm text-gray-600">
                                    {data.flagshipProject}
                                </div>
                            )}

                            {/* Profile Age - FINAL POLISH */}
                            {data.profileAge && (
                                <div className="mt-2 text-sm text-gray-600">
                                    {data.profileAge}
                                </div>
                            )}


                        </div>
                    </div>
                </div>
            </div>

            <div className="max-w-7xl mx-auto px-6 mt-8 space-y-6">

                {/* Recruiter Verdict - CRITICAL SECTION */}
                {data.recruiterVerdict && (
                    <div className={`card ${data.recruiterVerdict.decision === 'Strong Hire' ? 'bg-gradient-to-r from-green-50 to-emerald-50 border-green-300' :
                        data.recruiterVerdict.decision === 'Hire' ? 'bg-gradient-to-r from-blue-50 to-cyan-50 border-blue-300' :
                            data.recruiterVerdict.decision === 'Maybe' ? 'bg-gradient-to-r from-yellow-50 to-amber-50 border-yellow-300' :
                                'bg-gradient-to-r from-red-50 to-orange-50 border-red-300'
                        }`}>
                        <div className="flex items-center justify-between mb-4">
                            <h2 className="text-2xl font-bold text-gray-900">Recruiter Verdict</h2>
                            <span className={`px-6 py-2 rounded-full text-lg font-bold ${data.recruiterVerdict.decision === 'Strong Hire' ? 'bg-green-600 text-white' :
                                data.recruiterVerdict.decision === 'Hire' ? 'bg-blue-600 text-white' :
                                    data.recruiterVerdict.decision === 'Maybe' ? 'bg-yellow-600 text-white' :
                                        'bg-red-600 text-white'
                                }`}>
                                {data.recruiterVerdict.decision}
                            </span>
                        </div>
                        <p className="text-gray-800 text-lg leading-relaxed">
                            <strong>Decision Reasoning:</strong> {data.recruiterVerdict.shortReason}
                        </p>
                    </div>
                )}

                {/* Executive Summary */}
                {data.executiveSummary && (
                    <div className="card bg-gradient-to-r from-blue-50 to-indigo-50 border-blue-200">
                        <div className="flex items-center gap-2 mb-3">
                            <FileText className="text-blue-600" size={24} />
                            <h2 className="text-2xl font-bold text-gray-900">Executive Summary</h2>
                        </div>
                        <p className="text-gray-800 text-lg leading-relaxed">{data.executiveSummary}</p>
                    </div>
                )}

                {/* What Recruiters Notice First - CRITICAL SECTION */}
                {data.whatRecruitersNoticeFirst && data.whatRecruitersNoticeFirst.length > 0 && (
                    <div className="card bg-gradient-to-r from-purple-50 to-pink-50 border-purple-200">
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">⚡ What Recruiters Notice First</h2>
                        <p className="text-sm text-gray-600 mb-3">The first 30-second impression of your profile:</p>
                        <div className="space-y-2">
                            {data.whatRecruitersNoticeFirst.map((notice, idx) => (
                                <div key={idx} className="flex items-start gap-3 bg-white border border-purple-200 rounded-lg p-3">
                                    <div className="text-xl flex-shrink-0">•</div>
                                    <p className="text-gray-800 font-medium">{notice}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {/* Overall Assessment */}
                {data.overallAssessment && (
                    <div className="card">
                        <div className="flex items-center gap-2 mb-4">
                            <Award className="text-blue-600" size={24} />
                            <h2 className="text-2xl font-bold text-gray-900">Overall Assessment</h2>
                        </div>
                        <div className="grid md:grid-cols-3 gap-4 mb-4">
                            <div>
                                <div className="text-sm text-gray-600">Portfolio Strength</div>
                                <div className="text-lg font-semibold text-gray-900">
                                    {data.overallAssessment.portfolioStrength || 'N/A'}
                                </div>
                            </div>
                            <div>
                                <div className="text-sm text-gray-600">Engineering Level</div>
                                <div className="text-lg font-semibold text-gray-900">
                                    {data.overallAssessment.engineeringLevel || 'N/A'}
                                </div>
                            </div>
                            <div>
                                <div className="text-sm text-gray-600">Hire Readiness</div>
                                <div className="text-lg font-semibold text-gray-900">
                                    {data.overallAssessment.hireReadiness || 'N/A'}
                                </div>
                            </div>
                        </div>
                        {data.overallAssessment.confidenceReasoning && (
                            <div className="mt-4 pt-4 border-t border-gray-200">
                                <p className="text-gray-700 leading-relaxed">
                                    <strong>Reasoning:</strong> {data.overallAssessment.confidenceReasoning || 'N/A'}
                                </p>
                            </div>
                        )}

                        {/* Intelligence Features in Assessment */}
                        <div className="mt-4 pt-4 border-t border-gray-200 space-y-3">
                            {/* Weakest Dimension Alert */}
                            {data.weakestDimension && (
                                <div className="bg-red-50 border border-red-200 rounded-lg p-3">
                                    <p className="text-red-800 font-semibold">{data.weakestDimension}</p>
                                </div>
                            )}

                            {/* Portfolio Risk Summary - GROUPED WARNINGS */}
                            {(data.contributionConsistency || data.engineeringMaturity || data.languageFocus ||
                                data.commitQuality || data.repositoryNoiseCount > 0 || data.recruiterRiskSummary) && (
                                    <div className="bg-orange-50 border border-orange-200 rounded-lg p-4">
                                        <h4 className="font-semibold text-orange-900 mb-3">⚠ Portfolio Risk Summary</h4>
                                        <div className="space-y-2 text-sm">
                                            {/* 1. Recruiter Risk Summary (First) */}
                                            {data.recruiterRiskSummary && (
                                                <div className={`flex items-start gap-2 font-semibold ${data.recruiterRiskSummary.includes('High') ? 'text-red-700' :
                                                    data.recruiterRiskSummary.includes('Medium') ? 'text-orange-700' :
                                                        'text-green-700'
                                                    }`}>
                                                    <span>•</span>
                                                    <span>{data.recruiterRiskSummary}</span>
                                                </div>
                                            )}

                                            {/* 2. Engineering Maturity */}
                                            {data.engineeringMaturity && (
                                                <div className={`flex items-start gap-2 ${data.engineeringMaturity.includes('Production-Ready') ? 'text-green-700' :
                                                    data.engineeringMaturity.includes('Developing') ? 'text-blue-700' :
                                                        'text-gray-700'
                                                    }`}>
                                                    <span>•</span>
                                                    <span>{data.engineeringMaturity}</span>
                                                </div>
                                            )}

                                            {/* 3. Low Project Impact (New) */}
                                            {scoreBreakdown.projectImpact < 30 && (
                                                <div className="flex items-start gap-2 text-red-700">
                                                    <span>•</span>
                                                    <span>Low project impact ({scoreBreakdown.projectImpact}/100)</span>
                                                </div>
                                            )}

                                            {/* 4. Repository Noise (Incomplete Repos) */}
                                            {data.repositoryNoiseCount !== undefined && data.repositoryNoiseCount > 0 && (
                                                <div className="flex items-start gap-2 text-orange-700">
                                                    <span>•</span>
                                                    <span>{data.repositoryNoiseCount} repositories appear to be practice/demo projects</span>
                                                </div>
                                            )}

                                            {/* 5. Commit Quality (Project Depth) */}
                                            {data.commitQuality && (
                                                <div className={`flex items-start gap-2 ${data.commitQuality.includes('Healthy') ? 'text-green-700' :
                                                    data.commitQuality.includes('Shallow') ? 'text-red-700' :
                                                        'text-blue-700'
                                                    }`}>
                                                    <span>•</span>
                                                    <span>{data.commitQuality}</span>
                                                </div>
                                            )}
                                        </div>
                                    </div>
                                )}
                        </div>
                    </div>
                )}

                {/* Top 3 Strongest Repositories - INTELLIGENCE FEATURE */}
                {data.top3StrongestRepos && data.top3StrongestRepos.length > 0 && (
                    <div className="card bg-gradient-to-r from-yellow-50 to-amber-50 border-yellow-200">
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">🏆 Top 3 Strongest Repositories</h2>
                        <div className="space-y-2">
                            {data.top3StrongestRepos.map((repo, idx) => (
                                <div key={idx} className="flex items-center gap-3 bg-white border border-yellow-200 rounded-lg p-3">
                                    <div className="text-2xl">{idx === 0 ? '🥇' : idx === 1 ? '🥈' : '🥉'}</div>
                                    <p className="text-gray-900 font-semibold">{repo}</p>
                                    {idx === 0 && <span className="ml-auto text-sm text-yellow-600 font-bold">(Strongest)</span>}
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {/* Skill Categories - INTELLIGENCE FEATURE */}
                {data.skillCategories && (
                    <div className="card">
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">💻 Skill Category Breakdown</h2>
                        <div className="grid md:grid-cols-2 gap-4">
                            {data.skillCategories.backend && data.skillCategories.backend.length > 0 && (
                                <div className="bg-purple-50 border border-purple-200 rounded-lg p-4">
                                    <div className="text-sm font-semibold text-purple-700 mb-2">Backend</div>
                                    <div className="text-gray-900">{data.skillCategories.backend.join(', ')}</div>
                                </div>
                            )}
                            {data.skillCategories.frontend && data.skillCategories.frontend.length > 0 && (
                                <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
                                    <div className="text-sm font-semibold text-blue-700 mb-2">Frontend</div>
                                    <div className="text-gray-900">{data.skillCategories.frontend.join(', ')}</div>
                                </div>
                            )}
                            {data.skillCategories.mobile && data.skillCategories.mobile.length > 0 && (
                                <div className="bg-green-50 border border-green-200 rounded-lg p-4">
                                    <div className="text-sm font-semibold text-green-700 mb-2">Mobile</div>
                                    <div className="text-gray-900">{data.skillCategories.mobile.join(', ')}</div>
                                </div>
                            )}
                            {data.skillCategories.scripting && data.skillCategories.scripting.length > 0 && (
                                <div className="bg-orange-50 border border-orange-200 rounded-lg p-4">
                                    <div className="text-sm font-semibold text-orange-700 mb-2">Scripting</div>
                                    <div className="text-gray-900">{data.skillCategories.scripting.join(', ')}</div>
                                </div>
                            )}
                            {data.skillCategories.other && data.skillCategories.other.length > 0 && (
                                <div className="bg-gray-50 border border-gray-200 rounded-lg p-4">
                                    <div className="text-sm font-semibold text-gray-700 mb-2">Other</div>
                                    <div className="text-gray-900">{data.skillCategories.other.join(', ')}</div>
                                </div>
                            )}
                        </div>
                    </div>
                )}

                {/* Radar Chart */}
                <div className="card">
                    <h2 className="text-2xl font-bold text-gray-900 mb-6">Score Breakdown</h2>
                    <div className="h-80">
                        <ResponsiveContainer width="100%" height="100%">
                            <RadarChart data={radarData}>
                                <PolarGrid stroke="#e5e7eb" />
                                <PolarAngleAxis dataKey="metric" tick={{ fill: '#6b7280', fontSize: 14 }} />
                                <PolarRadiusAxis angle={90} domain={[0, 100]} tick={{ fill: '#6b7280' }} />
                                <Radar name="Score" dataKey="score" stroke="#2563eb" fill="#3b82f6" fillOpacity={0.5} />
                            </RadarChart>
                        </ResponsiveContainer>
                    </div>
                </div>

                {/* Strong Signals */}
                {data.strongSignals && data.strongSignals.length > 0 && (
                    <div className="card">
                        <div className="flex items-center gap-2 mb-4">
                            <CheckCircle className="text-green-600" size={24} />
                            <h2 className="text-2xl font-bold text-gray-900">Strong Signals</h2>
                        </div>
                        <div className="grid md:grid-cols-2 gap-4">
                            {data.strongSignals.map((signal, idx) => (
                                <div key={idx} className="bg-green-50 border border-green-200 rounded-lg p-4">
                                    <div className="flex items-start gap-3">
                                        <CheckCircle className="text-green-600 flex-shrink-0 mt-1" size={20} />
                                        <div>
                                            <h3 className="font-semibold text-gray-900 mb-1">{signal.signal}</h3>
                                            <p className="text-sm text-gray-700">{signal.whyItMatters}</p>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {/* Red Flags */}
                {data.redFlags && data.redFlags.length > 0 && (
                    <div className="card">
                        <div className="flex items-center gap-2 mb-4">
                            <AlertTriangle className="text-red-600" size={24} />
                            <h2 className="text-2xl font-bold text-gray-900">Red Flags</h2>
                        </div>
                        <div className="space-y-3">
                            {data.redFlags.map((flag, idx) => (
                                <div key={idx} className="bg-red-50 border border-red-200 rounded-lg p-4">
                                    <div className="flex items-start justify-between gap-4">
                                        <div className="flex items-start gap-3 flex-1">
                                            <AlertTriangle className="text-red-600 flex-shrink-0 mt-1" size={20} />
                                            <div>
                                                <h3 className="font-semibold text-gray-900 mb-1">{flag.issue}</h3>
                                                <p className="text-sm text-gray-700">{flag.impact}</p>
                                            </div>
                                        </div>
                                        <span className={`px-3 py-1 rounded-full text-xs font-medium border ${getSeverityColor(flag.severity)}`}>
                                            {flag.severity}
                                        </span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {/* Impact & Discoverability - CRITICAL SECTION */}
                {data.impactAndDiscoverability && (
                    <div className="card">
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">Impact & Discoverability</h2>
                        <div className="grid md:grid-cols-3 gap-4">
                            <div className="bg-purple-50 border border-purple-200 rounded-lg p-4">
                                <div className="text-sm text-gray-600 mb-2 font-semibold">Business Relevance</div>
                                <div className="text-base text-gray-900">{data.impactAndDiscoverability.businessRelevance}</div>
                            </div>
                            <div className="bg-indigo-50 border border-indigo-200 rounded-lg p-4">
                                <div className="text-sm text-gray-600 mb-2 font-semibold">Community Validation</div>
                                <div className="text-base text-gray-900">{data.impactAndDiscoverability.communityValidation}</div>
                            </div>
                            <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
                                <div className="text-sm text-gray-600 mb-2 font-semibold">Production Readiness</div>
                                <div className="text-base text-gray-900">{data.impactAndDiscoverability.productionReadiness}</div>
                            </div>
                        </div>
                    </div>
                )}

                {/* 30-Day Action Plan */}
                {data.thirtyDayActionPlan && (
                    <div className="card">
                        <div className="flex items-center gap-2 mb-4">
                            <Calendar className="text-blue-600" size={24} />
                            <h2 className="text-2xl font-bold text-gray-900">30-Day Improvement Roadmap</h2>
                        </div>
                        <div className="space-y-4">
                            {['week1', 'week2', 'week3', 'week4'].map((week, idx) => (
                                data.thirtyDayActionPlan[week] && (
                                    <div key={week} className="flex gap-4">
                                        <div className="flex-shrink-0">
                                            <div className="bg-blue-600 text-white rounded-lg px-4 py-2 font-semibold text-center">
                                                Week {idx + 1}
                                            </div>
                                        </div>
                                        <div className="flex-1 bg-blue-50 border border-blue-200 rounded-lg p-4">
                                            <p className="text-gray-800">{data.thirtyDayActionPlan[week]}</p>
                                        </div>
                                    </div>
                                )
                            ))}
                        </div>
                    </div>
                )}

                {/* Fix Priorities - POLISH FEATURE */}
                {data.fixPriorities && data.fixPriorities.length > 0 && (
                    <div className="card bg-gradient-to-r from-orange-50 to-red-50 border-orange-200">
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">🎯 Fix Priority Order</h2>
                        <p className="text-sm text-gray-600 mb-3">Focus on these areas first for maximum impact:</p>
                        <div className="space-y-2">
                            {data.fixPriorities.map((priority, idx) => (
                                <div key={idx} className="flex items-center gap-3 bg-white border border-orange-200 rounded-lg p-3">
                                    <div className="text-xl font-bold text-orange-600">{idx + 1}.</div>
                                    <p className="text-gray-800 font-medium">{priority}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {/* Completeness Stats - POLISH FEATURE */}
                {data.completenessStats && (
                    <div className="card bg-gradient-to-r from-cyan-50 to-blue-50 border-cyan-200">
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">📊 Portfolio Completeness</h2>
                        <div className="flex items-center justify-between mb-4">
                            <div className="text-5xl font-bold text-blue-600">
                                {data.completenessStats.professionalRepos}/{data.completenessStats.totalRepos}
                            </div>
                            <div className="text-right">
                                <div className="text-sm text-gray-600">Repositories Meeting</div>
                                <div className="text-sm text-gray-600">Professional Standards</div>
                            </div>
                        </div>
                        <p className="text-gray-700 text-base">{data.completenessStats.message}</p>
                        <p className="text-sm text-gray-500 mt-2">
                            Professional standard = README + (Tests OR Deployment OR Community Validation) + Recent Activity
                        </p>
                    </div>
                )}

                {/* Score Simulation - POLISH FEATURE */}
                {data.scoreSimulation && (
                    <div className="card bg-gradient-to-r from-green-50 to-emerald-50 border-green-200">
                        <h2 className="text-2xl font-bold text-gray-900 mb-4">💡 "If You Fix This" Projection</h2>
                        <div className="grid md:grid-cols-3 gap-4 mb-4">
                            <div className="bg-white border border-green-200 rounded-lg p-4 text-center">
                                <div className="text-sm text-gray-600 mb-1">Current Score</div>
                                <div className="text-3xl font-bold text-gray-900">{data.scoreSimulation.currentScore}</div>
                            </div>
                            <div className="bg-white border border-green-200 rounded-lg p-4 text-center">
                                <div className="text-sm text-gray-600 mb-1">Focus Area</div>
                                <div className="text-lg font-bold text-orange-600">{data.scoreSimulation.improvementArea}</div>
                            </div>
                            <div className="bg-white border border-green-200 rounded-lg p-4 text-center">
                                <div className="text-sm text-gray-600 mb-1">Projected Score</div>
                                <div className="text-3xl font-bold text-green-600">
                                    {data.scoreSimulation.projectedScore} {data.scoreSimulation.projectedGrade && `(${data.scoreSimulation.projectedGrade})`}
                                </div>
                            </div>
                        </div>
                        <p className="text-gray-800 text-base font-medium">{data.scoreSimulation.message}</p>
                    </div>
                )}

                {/* Resume-Ready Summary */}
                {data.resumeReadyProfileSummary && (
                    <div className="card bg-gradient-to-r from-green-50 to-emerald-50 border-green-200">
                        <div className="flex items-center gap-2 mb-4">
                            <FileText className="text-green-600" size={24} />
                            <h2 className="text-2xl font-bold text-gray-900">Resume-Ready Summary</h2>
                        </div>
                        <div className="bg-white border border-green-200 rounded-lg p-6">
                            <p className="text-gray-800 text-lg leading-relaxed whitespace-pre-line">
                                {data.resumeReadyProfileSummary}
                            </p>
                        </div>
                        <p className="text-sm text-gray-600 mt-3">
                            💡 Copy this summary to your LinkedIn profile or resume
                        </p>
                    </div>
                )}

            </div>
        </div >
    );
}
